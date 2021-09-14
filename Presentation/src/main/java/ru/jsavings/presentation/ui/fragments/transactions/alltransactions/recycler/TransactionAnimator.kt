package ru.jsavings.presentation.ui.fragments.transactions.alltransactions.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator

class TransactionAnimator(private val duration: Long) : SimpleItemAnimator() {

	override fun animateChange(
		oldHolder: RecyclerView.ViewHolder?,
		newHolder: RecyclerView.ViewHolder?,
		fromLeft: Int,
		fromTop: Int,
		toLeft: Int,
		toTop: Int,
	): Boolean = false
	override fun runPendingAnimations() {}
	override fun endAnimation(item: RecyclerView.ViewHolder) {}
	override fun endAnimations() {}
	override fun isRunning(): Boolean = false
	override fun animateRemove(holder: RecyclerView.ViewHolder?): Boolean = false
	override fun animateMove(holder: RecyclerView.ViewHolder?, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean =
		false

	override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
		holder?.let {
			it.itemView.animate()
				.alphaBy(0f)
				.alpha(1f)
				.yBy(0.5f)
				.y(1f)
				.setDuration(duration)
				.start()
		}
		return false
	}
}