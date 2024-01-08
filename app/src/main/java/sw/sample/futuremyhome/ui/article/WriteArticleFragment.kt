package sw.sample.futuremyhome.ui.article

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import sw.sample.futuremyhome.R
import sw.sample.futuremyhome.databinding.FragmentWriteBinding

class WriteArticleFragment : Fragment(R.layout.fragment_write){
    private lateinit var binding: FragmentWriteBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWriteBinding.bind(view)
    }

}