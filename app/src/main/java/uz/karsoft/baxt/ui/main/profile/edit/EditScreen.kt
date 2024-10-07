package uz.karsoft.baxt.ui.main.profile.edit

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.inject
import uz.karsoft.baxt.R
import uz.karsoft.baxt.databinding.LayoutEditProfileBinding
import uz.karsoft.baxt.settings.Settings

class EditScreenFragment: Fragment(R.layout.layout_edit_profile) {
    private lateinit var binding: LayoutEditProfileBinding
    private val settings: Settings by inject()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutEditProfileBinding.bind(view)
        binding.apply {
        }
    }
}