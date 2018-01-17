package ui.anwesome.com.imagefadecontainerview

/**
 * Created by anweshmishra on 18/01/18.
 */
import android.app.Activity
import android.view.*
import android.content.*
import android.graphics.*
class ImageFadeContainerView(ctx:Context, var bitmap1: Bitmap, var bitmap2:Bitmap):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = ImageFadeContainerRenderer(this)
    override fun onDraw(canvas:Canvas) {
        renderer.render(canvas,paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class ImageFadeContainer(var bitmap1:Bitmap,var bitmap2:Bitmap, var w:Float, var h:Float) {
        val state = ImageFadeContainerState()
        init {
            bitmap1 = Bitmap.createScaledBitmap(bitmap1, w.toInt(), w.toInt(), true)
            bitmap2 = Bitmap.createScaledBitmap(bitmap2, w.toInt(), w.toInt(), true)
        }
        fun draw(canvas:Canvas,paint:Paint) {
            canvas.save()
            canvas.translate(w/2,h/2)
            canvas.save()
            val path = Path()
            path.addRect(RectF( -w/2, -w/2, -w/2+w*(1-state.scale), w/2),Path.Direction.CW)
            canvas.clipPath(path)
            canvas.drawBitmap(bitmap1, -w/2, -w/2,paint)
            canvas.restore()
            canvas.save()
            val path1 = Path()
            path1.addRect(RectF(w/2 - w*state.scale, -w/2, w/2, w/2),Path.Direction.CW)
            canvas.clipPath(path1)
            canvas.drawBitmap(bitmap2, -w/2, -w/2, paint)
            canvas.restore()
            canvas.restore()
        }
        fun update(stopcb:(Float)->Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb:()->Unit) {
            state.startUpdating(startcb)
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
    data class ImageFadeContainerRenderer(var view:ImageFadeContainerView, var time:Int = 0) {
        var imageContainer:ImageFadeContainer?=null
        val animator = ImageFadeContainerAnimator(view)
        fun render(canvas:Canvas, paint:Paint) {
            if (time == 0) {
                val w = canvas.width.toFloat()
                val h = canvas.height.toFloat()
                imageContainer = ImageFadeContainer(view.bitmap1,view.bitmap2,w,h)
            }
            canvas.drawColor(Color.parseColor("#212121"))
            imageContainer?.draw(canvas,paint)
            time++
            animator.animate {
                imageContainer?.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            imageContainer?.startUpdating {
                animator.start()
            }
        }
    }
    data class ImageFadeContainerAnimator(var view:ImageFadeContainerView,var animated:Boolean = false) {
        fun animate(updatecb:()->Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex:Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    companion object {
        fun create(activity:Activity,bitmap1:Bitmap,bitmap2:Bitmap):ImageFadeContainerView {
            val view = ImageFadeContainerView(activity,bitmap1,bitmap2)
            activity.setContentView(view)
            return view
        }
    }
}