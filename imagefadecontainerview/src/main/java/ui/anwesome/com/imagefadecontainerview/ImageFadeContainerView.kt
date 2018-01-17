package ui.anwesome.com.imagefadecontainerview

/**
 * Created by anweshmishra on 18/01/18.
 */
import android.view.*
import android.content.*
import android.graphics.*
class ImageFadeContainerView(ctx:Context, var bitmap1: Bitmap, var bitmap2:Bitmap):View(ctx) {
    override fun onDraw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
    data class ImageFadeContainer(var bitmap1:Bitmap,var bitmap2:Bitmap, var w:Float, var h:Float) {
        init {
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, w.toInt(), w.toInt(), true)
            bitmap2 = Bitmap.createScaledBitmap(bitmap2, w.toInt(), w.toInt(), true)
        }
        fun draw(canvas:Canvas,paint:Paint) {
            canvas.save()
            canvas.translate(w/2,w/2)
            val path = Path()
            path.addRect(RectF( -w/2, -w/2, -w/2+w, w/2),Path.Direction.CW)
            canvas.drawBitmap(bitmap1, -w/2, -w/2,paint)
            path.addRect(RectF(w/2 - w, -w/2, w/2, w),Path.Direction.CW)
            canvas.drawBitmap(bitmap2, -w/2, -w/2, paint)
            canvas.restore()
        }
        fun update(stopcb:(Float)->Unit) {

        }
        fun startUpdating(startcb:()->Unit) {

        }
    }
    data class ImageFadeContainerState(var scale:Float = 0f,var dir:Float = 0f,var prevScale:Float = 0f) {
        fun update(stopcb:(Float)->Unit) {
            scale += 0.1f*dir
            if(Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }
        fun startUpdating(startcb:()->Unit) {
            if(dir == 0f) {
                dir = 1 - 2*scale
                startcb()
            }
        }
    }
}