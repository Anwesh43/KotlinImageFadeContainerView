package ui.anwesome.com.imagefadecontainerview

/**
 * Created by anweshmishra on 18/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class ImageFadeContainerView(ctx:Context):View(ctx) {
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}