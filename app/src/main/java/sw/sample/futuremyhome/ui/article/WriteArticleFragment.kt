package sw.sample.futuremyhome.ui.article

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import sw.sample.futuremyhome.R
import sw.sample.futuremyhome.databinding.FragmentWriteBinding
import java.util.UUID

class WriteArticleFragment : Fragment(R.layout.fragment_write){
    private lateinit var binding: FragmentWriteBinding
    private var selectedUri : Uri? = null
    private val pickMedia = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        // Callback is invoked after the user selects a media item or closes the
        // photo picker.
        if (uri != null) {
            Log.d("PhotoPicker", "Selected URI: $uri")
            binding.photoImageView.setImageURI(uri)
            selectedUri = uri
        } else {
            Log.d("PhotoPicker", "No media selected")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWriteBinding.bind(view)
        pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

        binding.photoImageView.setOnClickListener {  }

        binding.clearButton.setOnClickListener {  }

        binding.submitButton.setOnClickListener {
            binding.progressbarFrame.isVisible = true
            if( selectedUri != null){
                val photoUri = selectedUri ?: return@setOnClickListener
                val fileName = "${UUID.randomUUID()}.png"
                Firebase.storage.reference.child("articles/photo").child(fileName)
                    .putFile(photoUri)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            Snackbar.make(view,"addOnCompleteListener", Snackbar.LENGTH_SHORT).show()
                            Firebase.storage.reference.child("articles/photo").child(fileName)
                                .downloadUrl
                                .addOnSuccessListener {
                                    Log.i("downloadUri", "$it")
                                    binding.progressbarFrame.isVisible = false
                                }
                        }else{
                            //error Handler
                        }
                    }
            }else{
                Snackbar.make(view,"이미지 선택해주세요", Snackbar.LENGTH_SHORT).show()
            }

        }

        binding.backbutton.setOnClickListener {
            findNavController().navigate(WriteArticleFragmentDirections.actionBack())
        }



    }//override fun onViewCreated


}//class