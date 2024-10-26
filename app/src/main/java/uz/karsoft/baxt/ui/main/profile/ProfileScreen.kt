package uz.karsoft.baxt.ui.main.profile

import android.animation.ObjectAnimator
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import org.koin.android.ext.android.inject
import uz.karsoft.baxt.R
import uz.karsoft.baxt.databinding.LayoutProfileBinding
import uz.karsoft.baxt.extensions.onClick
import uz.karsoft.baxt.settings.Settings

class ProfileScreen: Fragment(R.layout.layout_profile) {
    private lateinit var binding: LayoutProfileBinding
    private val settings: Settings by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LayoutProfileBinding.bind(view)
        binding.apply {
            tvName.text = settings.name
            ivEdit.onClick {
                val scaleX = ObjectAnimator.ofFloat(ivEdit, "scaleX", 1f, 1.2f, 1f)
                val scaleY = ObjectAnimator.ofFloat(ivEdit, "scaleY", 1f, 1.2f, 1f)
                scaleX.duration = 500
                scaleY.duration = 500
                scaleX.start()
                scaleY.start()
                findNavController().navigate(R.id.action_nav_profile_to_editScreenFragment)
            }
            ivRight.setOnClickListener {
                val scaleX = ObjectAnimator.ofFloat(ivRight, "scaleX", 1f, 1.2f, 1f)
                val scaleY = ObjectAnimator.ofFloat(ivRight, "scaleY", 1f, 1.2f, 1f)
                scaleX.duration = 500
                scaleY.duration = 500
                scaleX.start()
                scaleY.start()
                showBottomSheet()
            }

            ivEnd.onClick {
                val scaleX = ObjectAnimator.ofFloat(ivEnd, "scaleX", 1f, 1.2f, 1f)
                val scaleY = ObjectAnimator.ofFloat(ivEnd, "scaleY", 1f, 1.2f, 1f)
                scaleX.duration = 500
                scaleY.duration = 500
                scaleX.start()
                scaleY.start()
                findNavController().navigate(R.id.action_nav_profile_to_settingsFragment)
            }

        }

    }
    private fun showBottomSheet(){
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_balance, null)
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetView.findViewById<CardView>(R.id.cardView_3).setOnClickListener {

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }
}