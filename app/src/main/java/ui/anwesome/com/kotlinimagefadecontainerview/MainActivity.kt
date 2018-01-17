package ui.anwesome.com.kotlinimagefadecontainerview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.imagefadecontainerview.ImageFadeContainerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImageFadeContainerView.create(this,getBitmap(R.drawable.nature),getBitmap(R.drawable.nature1))
    }
    fun getBitmap(drawable:Int):Bitmap = BitmapFactory.decodeResource(resources,drawable)
}

