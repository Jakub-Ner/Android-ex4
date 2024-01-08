package com.example.exercise04.photos

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.exercise04.BuildConfig
import com.example.exercise04.R
import com.example.exercise04.databinding.FragmentPhotoListBinding
import com.example.exercise04.photos.DataRepo.Companion.PRIVATE_S
import com.example.exercise04.photos.DataRepo.Companion.SHARED_S
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date


class PhotoListFragment : Fragment() {
    private var _binding: FragmentPhotoListBinding? = null
    private val binding get() = _binding!!

    private lateinit var dataRepo: DataRepo
    private lateinit var adapter: PhotoListAdapter
    private lateinit var recView: RecyclerView

    private val APP_STORAGE = "App Storage"
    private val SHARED_STORAGE = "Shared Storage"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =
            FragmentPhotoListBinding.inflate(inflater, container, false)
        dataRepo = DataRepo.getinstance(requireContext())
        dataRepo.setStorage(PRIVATE_S)

        binding.btnStorageType.text = SHARED_STORAGE

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("PhotoListFragment", "onViewCreated")
        recView = _binding!!.recView

        val listener = object : OnItemClickListener {
            override fun onImageClick(dataItem: DataItem) {
                val bundle = Bundle()
                bundle.putInt("selectedItemId", dataItem.id)
                findNavController().navigate(R.id.swipePhotoFragment, bundle)
            }
        }


        val adapter = dataRepo.getList() // replace with getAppLiist to access app-only imgs
            ?.let { PhotoListAdapter(requireContext(), it, listener) }
        if (adapter == null) {
            Toast.makeText(
                requireContext(), "Invalid Data",
                Toast.LENGTH_LONG
            ).show()
            requireActivity().onBackPressed()
        }
//        recView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        recView.layoutManager = GridLayoutManager(requireContext(), 2)
        this.adapter = adapter!!
        recView.adapter = adapter

        var newImage: DataItem? = null;

        val photoLauncher =
            registerForActivityResult(ActivityResultContracts.TakePicture()) { result: Boolean ->
                if (result) { // consume result - see later remarks
                    Toast.makeText(requireContext(), "Photo TAKEN", Toast.LENGTH_LONG).show()

//                    this.adapter.dList.add(newImage!!)
//                    list?.add(newImage!!)
                    dataRepo.addDataItem(newImage!!)
                    this.adapter.notifyItemInserted(newImage!!.id)

                } else { // make some action – warning
                    Toast.makeText(requireContext(), "Photo NOT taken!", Toast.LENGTH_LONG).show()
                }
            }

        binding.btnAddFromGallery.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 200)
        }
        binding.btnTakePhoto.setOnClickListener {
            try {
                val tmpUri = getNewFileUri()
                val value = File(tmpUri.path!!)
                val fileName = value.name
                photoLauncher.launch(tmpUri)

                newImage = DataItem(
                    dataRepo.getListSize(),
                    fileName,
                    value.toURI().path,
                    value.absolutePath,
                    tmpUri
                )
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(requireContext(), "CAMERA DOESN'T WORK!", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnStorageType.setOnClickListener {
            if (DataRepo.getStorage() == SHARED_S) {
                dataRepo.setStorage(PRIVATE_S)
                binding.btnStorageType.text = SHARED_STORAGE
            } else {
                dataRepo.setStorage(SHARED_S)
                binding.btnStorageType.text = APP_STORAGE
            }
            this.adapter.dList.clear()
            dataRepo.getList()
            adapter.notifyDataSetChanged()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("PhotoListFragment", "onActivityResult1")
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("PhotoListFragment", "onActivityResult2")

        when (requestCode) {
            200 -> {
                val selectedImageUri = data?.data
                Log.d("PhotoListFragment", "selectedImageUri: $selectedImageUri")
                val data =
                    requireContext().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                data.edit().putString("image", selectedImageUri.toString()).apply()
                findNavController().navigate(R.id.action_nav_photo_list_fragment_to_nav_home)
            }
        }
    }

    private fun getNewFileUri(): Uri {
        val dir: File
        when (DataRepo.getStorage()) {
            SHARED_S -> dir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

            PRIVATE_S -> dir =
                requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)!!

            else -> return MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val tStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val tmpFile = File.createTempFile(
            "Photo_" + "${tStamp}",
            ".jpg",
            dir
        )
        return FileProvider.getUriForFile(
            requireContext(),
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

}