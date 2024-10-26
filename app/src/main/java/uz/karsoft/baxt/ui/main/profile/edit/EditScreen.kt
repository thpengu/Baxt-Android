package uz.karsoft.baxt.ui.main.profile.edit

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
            tvBack.setOnClickListener {
                val scaleX = ObjectAnimator.ofFloat(tvBack, "scaleX", 1f, 1.2f, 1f)
                val scaleY = ObjectAnimator.ofFloat(tvBack, "scaleY", 1f, 1.2f, 1f)
                scaleX.duration = 500
                scaleY.duration = 500
                scaleX.start()
                scaleY.start()
                findNavController().navigate(R.id.action_editScreenFragment_to_nav_profile)

            }

        }
    }
}