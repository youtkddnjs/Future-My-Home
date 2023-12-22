package sw.sample.futuremyhome

import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import sw.sample.futuremyhome.databinding.FrgmentAuthBinding

class AuthFrgment: Fragment(R.layout.frgment_auth){

    private lateinit var binding : FrgmentAuthBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FrgmentAuthBinding.bind(view)
        setupsignUpButton()
        setupSignInOutButton()
    }

    private fun setupsignUpButton() {
        binding.signUpButton.setOnClickListener {
            hideKeyBoard()
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            if (email.isEmpty() || password.isEmpty()){
                Snackbar.make(binding.root, "입력해주세요.", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Firebase.auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful){
                        Snackbar.make(binding.root, "회원가입 완료", Snackbar.LENGTH_SHORT).show()
                        initViewToSignIn()
                    }else{
                        Snackbar.make(binding.root, "회원가입 실패", Snackbar.LENGTH_SHORT).show()
                    }
                }//.addOnCompleteListener
        }
    }//private fun setupsignUpButton()

    private fun setupSignInOutButton() {
        binding.signInOutButton.setOnClickListener {
            hideKeyBoard()
            val email = binding.emailET.text.toString()
            val password = binding.passwordET.text.toString()
            if(Firebase.auth.currentUser == null){
                //로그인
                if (email.isEmpty() || password.isEmpty()){
                    Snackbar.make(binding.root, "입력해주세요.", Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                Firebase.auth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful){
                            //로그인 성공
                            Snackbar.make(binding.root, "로그인 성공", Snackbar.LENGTH_SHORT).show()
                            initViewToSignIn()
                        } else{
                            Snackbar.make(binding.root, "로그인 실패", Snackbar.LENGTH_SHORT).show()
                        }
                    }

            }else{
                //로그아웃
                Firebase.auth.signOut()
                initViewToSignOut()

            }
        }
    }//private fun setupSignInOutButton()

    private fun initViewToSignIn(){
        binding.emailET.setText(Firebase.auth.currentUser?.email)
        binding.emailET.isEnabled = false
        binding.passwordET.isVisible = false
        binding.signInOutButton.text= getString(R.string.signOut)
        binding.signUpButton.isEnabled = false

        val action = AuthFrgmentDirections.actionAuthFrgmentToHomeFragment()
        findNavController().navigate(action)

    }//private fun initViewToSignIn()

    private fun initViewToSignOut(){
        binding.emailET.text.clear()
        binding.emailET.isEnabled = true
        binding.passwordET.text.clear()
        binding.passwordET.isVisible = true
        binding.signInOutButton.text= getString(R.string.signIn)
        binding.signUpButton.isEnabled = true
    }//private fun initViewToSignOut()

    override fun onStart() {
        super.onStart()
        if(Firebase.auth.currentUser == null){
            initViewToSignOut()
        }else{
            initViewToSignIn()
        }
    }

    private fun hideKeyBoard(){
        val hideKB = context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
//        hideKB.hideSoftInputFromWindow( this.currentFocus?.windowToken, 0)
    }

}