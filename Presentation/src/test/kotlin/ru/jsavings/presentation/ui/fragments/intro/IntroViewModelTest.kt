package ru.jsavings.presentation.ui.fragments.intro

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.google.common.truth.Truth
import io.mockk.*
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import ru.jsavings.domain.model.database.Account
import ru.jsavings.domain.usecase.cache.CacheKeys
import ru.jsavings.domain.usecase.cache.CacheUseCase
import ru.jsavings.domain.usecase.database.account.GetAllAccountsUseCase
import ru.jsavings.presentation.viewmodels.common.BaseViewModel

class IntroViewModelTest {

	companion object {
		private const val SOME_DEFAULT_LONG_VALUE = -1L
		private const val ID = 1L
		private const val NAME = "some name"
		private const val CURRENCY = "some currency"
		private const val BALANCE = 25.0
	}

	private val someAccountModel = Account(
		accountId = ID,
		name = NAME,
		mainCurrencyCode = CURRENCY,
		balanceInMainCurrency = BALANCE
	)

	@Rule
	@JvmField
	val rule = InstantTaskExecutorRule()

	private lateinit var getAllAccountsUseCase: GetAllAccountsUseCase
	private lateinit var cacheUseCase: CacheUseCase
	private lateinit var viewModel: IntroViewModel

	private val getAllAccountsStateObserver: Observer<BaseViewModel.RequestState> = mockk()

	private val accountsList = emptyList<Account>()
	private val someError = Throwable()

	@Before
	fun setUp() {
		getAllAccountsUseCase = mockk()
		cacheUseCase = mockk()
		viewModel = IntroViewModel(getAllAccountsUseCase, cacheUseCase)

		mockkStatic(Schedulers::class)
		mockkStatic(AndroidSchedulers::class)
		RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
		RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
		every { Schedulers.io() } returns Schedulers.trampoline()
		every { AndroidSchedulers.mainThread() } returns Schedulers.trampoline()

		viewModel.allAccountsRequestState.observeForever(getAllAccountsStateObserver)

//		every { getAllAccountsStateObserver.onChanged(any()) } answers {}
	}

	@After
	fun tearDown() {
		unmockkStatic(Schedulers::class)
		unmockkStatic(AndroidSchedulers::class)
	}

	@Test
	fun `request all accounts success test`() {

		//Act
		every { getAllAccountsStateObserver.onChanged(BaseViewModel.RequestState.SendingState) } answers {}
		every { getAllAccountsStateObserver.onChanged(BaseViewModel.RequestState.SuccessState(accountsList)) } answers {}
		every { getAllAccountsUseCase() } returns Single.just(accountsList)
		viewModel.requestAllAccounts()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			getAllAccountsStateObserver.onChanged(BaseViewModel.RequestState.SendingState)
			getAllAccountsStateObserver.onChanged(BaseViewModel.RequestState.SuccessState(accountsList))
		}
	}

	@Test
	fun `request all accounts error test`() {

		//Act
		every { getAllAccountsUseCase() } returns Single.error(someError)
		viewModel.requestAllAccounts()

		//Assert
		verify(ordering = Ordering.ORDERED) {
			getAllAccountsStateObserver.onChanged(BaseViewModel.RequestState.SendingState)
			getAllAccountsStateObserver.onChanged(BaseViewModel.RequestState.ErrorState(someError))
		}
	}

	@Test
	fun getAccountFromCache() {

		//Act
		every { cacheUseCase.get(CacheKeys.JS_CURRENT_ACCOUNT, SOME_DEFAULT_LONG_VALUE) } returns
				SOME_DEFAULT_LONG_VALUE
		viewModel.getAccountFromCache()

		//Assert
		verify { cacheUseCase.get(CacheKeys.JS_CURRENT_ACCOUNT, SOME_DEFAULT_LONG_VALUE) }
	}

	@Test
	fun `choose account success test`() {

		//Arrange
		val expectedResult = someAccountModel.accountId

		//Act
		every { cacheUseCase.put(CacheKeys.JS_CURRENT_ACCOUNT, someAccountModel.accountId) } answers {}
		val actualResult = viewModel.chooseAccount(listOf(someAccountModel))

		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}

	@Test
	fun `choose account error test`() {

		//Arrange
		val expectedResult = SOME_DEFAULT_LONG_VALUE

		//Act
		val actualResult = viewModel.chooseAccount(emptyList())

		//Assert
		Truth.assertThat(actualResult).isEqualTo(expectedResult)
	}
}