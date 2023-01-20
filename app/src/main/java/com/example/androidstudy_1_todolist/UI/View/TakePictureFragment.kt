package com.example.androidstudy_1_todolist.UI.View

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.androidstudy_1_todolist.BaseActivity
import com.example.androidstudy_1_todolist.MainActivity
import com.example.androidstudy_1_todolist.R
import com.example.androidstudy_1_todolist.databinding.ModifyFragmentBinding
import com.example.androidstudy_1_todolist.databinding.TakePictureFragmentBinding
import kotlinx.android.synthetic.main.take_picture_fragment.*
import kotlinx.android.synthetic.main.take_picture_fragment.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat

class TakePictureFragment : Fragment() {
    val REQ_CAMERA = 101

    var realUri : Uri? = null
    private lateinit var binding: TakePictureFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val contract = ActivityResultContracts.RequestPermission()

        /** 카메라 사용 권한에 대해 요청한다. */
        val activityResultLauncher =
            registerForActivityResult(contract) { isGranted ->
                if (isGranted) {

                } else {
                    Toast.makeText(getActivity(), "카메라 권한을 승인해야 카메라를 사용할 수 있습니다",
                        Toast.LENGTH_SHORT).show()
                }
            }
        activityResultLauncher.launch(Manifest.permission.CAMERA)
        // activityResultLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TakePictureFragmentBinding.inflate(inflater, container, false)

        /** 버튼을 눌러 사진을 찍는다. */
        binding.btnCapture.setOnClickListener {
            openCamera()
        }

        return binding.root
    }

    fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        createImageUri(newFileName(), "image/jpg")?.let {uri ->
            realUri = uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, realUri)
            startActivityForResult(intent, REQ_CAMERA)
        }
    }

    /** MediaStore : 외부 저장소를 관리하는 데이터베이스 */

    /** 촬영한 이미지를 저장할 Uri를 미디어스토어에 생성하는 메소드*/
    fun createImageUri(filename: String, mimeType: String): Uri? {
        var values = ContentValues()
        values.put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        values.put(MediaStore.Images.Media.MIME_TYPE, mimeType)
        return requireActivity().contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
    }

    /** 파일명을 만들어 주는 함수 */
    fun newFileName(): String {
        val sdf = SimpleDateFormat("yyyyMMDD_HHmmss")
        val filename = sdf.format(System.currentTimeMillis())
        return "$filename.jpg"
    }

    /** Uri를 사용해서 미디어스토어에 저장된 이미지를 읽어오는 메소드
     * Uri를 받아서 Bitmap으로 변환해주는 작업을 진행한다.
     * 버전에 맞게 메소드를 다르게 사용한다. */
    fun loadBitmap(photoUri: Uri): Bitmap? {
       var image: Bitmap? = null
        try {
            image = if (Build.VERSION.SDK_INT > 27) {
                val source: ImageDecoder.Source =
                    ImageDecoder.createSource(this.requireActivity().contentResolver, photoUri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(this.requireActivity().contentResolver, photoUri)
            }
        } catch (e: IOException) {
            //e.pritStackTrace()
        }
        return image
    }

    /** 화면에 세팅해준다. */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQ_CAMERA -> {
                    realUri?.let { uri ->
                        val bitmap = loadBitmap(uri)
                        binding.imgCapture.setImageBitmap(bitmap)
                        realUri = null
                    }
                }
            }
        }
    }
}