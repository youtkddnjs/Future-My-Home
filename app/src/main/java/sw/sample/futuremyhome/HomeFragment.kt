package sw.sample.futuremyhome

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import sw.sample.futuremyhome.data.ArticleModel

class HomeFragment: Fragment(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = Firebase.firestore
        db.collection("articles").document("ftYOeW9WUVtw9iad6U9q").get()
            .addOnSuccessListener { result ->
//                val map = result.data
                val article = result.toObject<ArticleModel>()
                Log.i("article", "article : $article")
            }
            .addOnFailureListener {
                it.printStackTrace()
            }
    }
}