package com.example

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)

        WindowCompat.setDecorFitsSystemWindows(window, false)
        window.statusBarColor = Color.TRANSPARENT
        window.navigationBarColor = Color.TRANSPARENT

        val backButton = findViewById<ImageButton>(R.id.backButton)
        val recyclerView = findViewById<RecyclerView>(R.id.galleryRecyclerView)
        val specialMessageButton = findViewById<Button>(R.id.specialMessageButton)
        
        backButton.setOnClickListener {
            finish()
        }

        // Dynamically discover all anushka_1, anushka_2, ... drawables
        val images = mutableListOf<Int>()
        for (i in 1..100) {
            val resId = resources.getIdentifier("anushka_$i", "drawable", packageName)
            if (resId != 0) {
                images.add(resId)
            } else {
                break
            }
        }
        
        // Fallback: If no anushka_ images, check for anushka_photo
        if (images.isEmpty()) {
            val mainPhoto = resources.getIdentifier("anushka_photo", "drawable", packageName)
            if (mainPhoto != 0) images.add(mainPhoto)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = GalleryAdapter(this, images)
        
        specialMessageButton.setOnClickListener {
            val intent = Intent(this, SpecialMessageActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        MusicManager.start(this, R.raw.new_song)
    }

    override fun onPause() {
        super.onPause()
        MusicManager.pause()
    }
}

class GalleryAdapter(
    private val context: Context,
    private val imageResList: List<Int>
) : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.galleryImageView)
        var loadJob: Job? = null
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_gallery_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val resId = imageResList[position]
        
        // Cancel any pending load job for recycled view to avoid race conditions
        holder.loadJob?.cancel()
        holder.imageView.setImageBitmap(null)
        
        holder.loadJob = CoroutineScope(Dispatchers.IO).launch {
            val bitmap = decodeSampledBitmapFromResource(context.resources, resId, 320, 320)
            withContext(Dispatchers.Main) {
                holder.imageView.setImageBitmap(bitmap)
            }
        }
        
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ImageViewerActivity::class.java)
            intent.putExtra("imageResId", resId)
            context.startActivity(intent)
        }
    }

    override fun onViewRecycled(holder: ViewHolder) {
        super.onViewRecycled(holder)
        holder.loadJob?.cancel()
        holder.imageView.setImageBitmap(null)
    }

    override fun getItemCount(): Int = imageResList.size

    private fun decodeSampledBitmapFromResource(
        res: android.content.res.Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }
        BitmapFactory.decodeResource(res, resId, options)

        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false

        return BitmapFactory.decodeResource(res, resId, options)
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.outHeight to options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2
            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }
}
