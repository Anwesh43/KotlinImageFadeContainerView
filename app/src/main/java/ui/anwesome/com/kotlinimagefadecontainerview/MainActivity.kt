package ui.anwesome.com.kotlinimagefadecontainerview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import ui.anwesome.com.imagefadecontainerview.ImageFadeContainerView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = ImageFadeContainerView.create(this,getBitmap(R.drawable.nature),getBitmap(R.drawable.nature1))
        view.addImageFadeContainerListener({
            Toast.makeText(this,"Image1 is shown",Toast.LENGTH_SHORT).show()
        },{
            Toast.makeText(this,"Image2 is shown",Toast.LENGTH_SHORT).show()
        })
    }
    fun getBitmap(drawable:Int):Bitmap = BitmapFactory.decodeResource(resources,drawable)
}

