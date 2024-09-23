package uz.karsoft.baxt.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import uz.karsoft.baxt.R
import uz.karsoft.baxt.databinding.LayoutLoginBinding
import uz.karsoft.baxt.databinding.LayoutProfileBinding
import uz.karsoft.baxt.settings.Settings

class ProfileScreen: Fragment(R.layout.layout_profile) {
    private lateinit var binding: LayoutProfileBinding
    private val settings: Settings by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutProfileBinding.bind(view)
        binding.apply {
            tvName.text = settings.name
        }
    }
}