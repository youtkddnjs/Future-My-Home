package sw.sample.futuremyhome.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import sw.sample.futuremyhome.R
import sw.sample.futuremyhome.data.ArticleModel
import sw.sample.futuremyhome.databinding.FragmentHomeBinding

class HomeFragment: Fragment(R.layout.fragment_home) {
    private lateinit var binding : FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        val db = Firebase.firestore
        db.collection("articles").document("ftYOeW9WUVtw9iad6U9q").get()
            .addOnSuccessListener { result ->
//                val map = result.data 이렇게도 가능
                val article = result.toObject<ArticleModel>()
                Log.i("article", "article : $article")
            }
            .addOnFailureListener {
                it.printStackTrace()
            }


            
            setupWriteButton(view)


    }//fun onViewCreated

    private fun setupWriteButton(view: View) {
        binding.addbutton.setOnClickListener {
            if(Firebase.auth.currentUser != null){
                val action = HomeFragmentDirections.actionHomeFragmentToWriteArticleFragment()
                findNavController().navigate(action)
            }else{
                Snackbar.make(view, "로그인 후 사용해주세요.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }//fun setupWriteButton
}