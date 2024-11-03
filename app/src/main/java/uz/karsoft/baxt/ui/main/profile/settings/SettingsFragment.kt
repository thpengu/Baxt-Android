package uz.karsoft.baxt.ui.main.profile.settings

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
import uz.karsoft.baxt.MainActivity
import uz.karsoft.baxt.R
import uz.karsoft.baxt.databinding.FragmentSettingsBinding
import uz.karsoft.baxt.extensions.onClick
import uz.karsoft.baxt.settings.Settings
import uz.karsoft.baxt.ui.main.profile.adapter.SettingsAdapter

class SettingsFragment : Fragment(R.layout.fragment_settings) {
   private lateinit var binding: FragmentSettingsBinding
   private val settings: Settings by inject()
   private lateinit var settingsAdapter: SettingsAdapter
   private val itemsList: MutableList<String> = mutableListOf("Настройки")

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
      super.onViewCreated(view, savedInstanceState)
      binding = FragmentSettingsBinding.bind(view)

      setupRecyclerView()
      dayNightButtonFun()
      notificationButtonFun()
   }

   private fun setupRecyclerView() {

      settingsAdapter = SettingsAdapter(itemsList) { selectedItem ->
         when (selectedItem) {
            "Настройки" -> { findNavController().navigate(R.id.action_settingsFragment_to_nav_profile)}
            "Общие настройки" ->{
               itemsList.remove("Общие настройки")
               settingsAdapter.notifyDataSetChanged()
               binding.constraintLayout1.visibility=View.VISIBLE
               binding.constraintLayout2.visibility=View.GONE
            }
            "Настройки интерфейса" ->{
               itemsList.remove("Настройки интерфейса")
               settingsAdapter.notifyDataSetChanged()
               binding.constraintLayout1.visibility=View.VISIBLE
               binding.constraintLayout2.visibility=View.GONE
               binding.constraintLayout3.visibility=View.GONE
            }
            "Прочие настройки" ->{
               itemsList.remove("Прочие настройки")
               itemsList.remove("Помощь и поддержка")
               itemsList.remove("Обратная связь")
               itemsList.remove("О приложении")
               settingsAdapter.notifyDataSetChanged()
               binding.constraintLayout1.visibility=View.VISIBLE
               binding.constraintLayout2.visibility=View.GONE
               binding.constraintLayout3.visibility=View.GONE
               binding.constraintLayout4.visibility=View.GONE
               binding.constraintLayout5.visibility=View.GONE
               binding.constraintLayout6.visibility=View.GONE
               binding.constraintLayout7.visibility=View.GONE
            }
            "Помощь и поддержка" ->{
               itemsList.remove("Помощь и поддержка")
               settingsAdapter.notifyDataSetChanged()
               binding.constraintLayout1.visibility=View.GONE
               binding.constraintLayout2.visibility=View.GONE
               binding.constraintLayout3.visibility=View.GONE
               binding.constraintLayout4.visibility=View.VISIBLE
               binding.constraintLayout5.visibility=View.GONE
            }
            "О приложении" ->{
               itemsList.remove("О приложении")
               settingsAdapter.notifyDataSetChanged()
               binding.constraintLayout1.visibility=View.GONE
               binding.constraintLayout2.visibility=View.GONE
               binding.constraintLayout3.visibility=View.GONE
               binding.constraintLayout4.visibility=View.VISIBLE
               binding.constraintLayout5.visibility=View.GONE
               binding.constraintLayout6.visibility=View.GONE
            }
            "Обратная связь" ->{
               itemsList.remove("Обратная связь")
               settingsAdapter.notifyDataSetChanged()
               binding.constraintLayout1.visibility=View.GONE
               binding.constraintLayout2.visibility=View.GONE
               binding.constraintLayout3.visibility=View.GONE
               binding.constraintLayout4.visibility=View.VISIBLE
               binding.constraintLayout5.visibility=View.GONE
               binding.constraintLayout6.visibility=View.GONE
               binding.constraintLayout7.visibility=View.GONE
            }
         }
      }

      // RecyclerView sozlash va adapterni ulash
      binding.rvNavigation.apply {
         layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
         adapter = settingsAdapter
      }
      binding.cardView1.onClick {
         binding.apply {
            itemsList.add("Общие настройки")
            settingsAdapter.notifyDataSetChanged()
            constraintLayout1.visibility=View.GONE
            constraintLayout2.visibility=View.VISIBLE
         }
      }
      binding.cardView2.onClick {
         binding.apply {
            itemsList.add("Настройки интерфейса")
            settingsAdapter.notifyDataSetChanged()
            constraintLayout1.visibility=View.GONE
            constraintLayout2.visibility=View.GONE
            constraintLayout3.visibility=View.VISIBLE
         }
      }
      binding.cardView3.onClick {
         binding.apply {
            itemsList.add("Прочие настройки")
            settingsAdapter.notifyDataSetChanged()
            constraintLayout1.visibility=View.GONE
            constraintLayout2.visibility=View.GONE
            constraintLayout3.visibility=View.GONE
            constraintLayout4.visibility=View.VISIBLE
         }
      }
      binding.cardView10.onClick {
         binding.apply {
            itemsList.add("Помощь и поддержка")
            settingsAdapter.notifyDataSetChanged()
            constraintLayout1.visibility=View.GONE
            constraintLayout2.visibility=View.GONE
            constraintLayout3.visibility=View.GONE
            constraintLayout4.visibility=View.GONE
            constraintLayout5.visibility=View.VISIBLE
         }
      }
      binding.cardView11.onClick {
         binding.apply {
            itemsList.add("О приложении")
            settingsAdapter.notifyDataSetChanged()
            constraintLayout1.visibility=View.GONE
            constraintLayout2.visibility=View.GONE
            constraintLayout3.visibility=View.GONE
            constraintLayout4.visibility=View.GONE
            constraintLayout5.visibility=View.GONE
            constraintLayout6.visibility=View.VISIBLE
         }
      }
      binding.cardView12.onClick {
         binding.apply {
            itemsList.add("Обратная связь")
            settingsAdapter.notifyDataSetChanged()
            constraintLayout1.visibility=View.GONE
            constraintLayout2.visibility=View.GONE
            constraintLayout3.visibility=View.GONE
            constraintLayout4.visibility=View.GONE
            constraintLayout5.visibility=View.GONE
            constraintLayout6.visibility=View.GONE
            constraintLayout7.visibility=View.VISIBLE
         }
      }

   }
   private fun dayNightButtonFun() {
      binding.apply {
         val preferences = requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
         val editor = preferences.edit()

// Dastlabki holatni olish
         val isNightMode = preferences.getBoolean("night_mode", false)
         dayNightButton.isChecked = isNightMode // Switch holatini yangilang

// Ranglarni belgilash
         val greenColor = ContextCompat.getColor(requireContext(), R.color.green) // Yashil rang
         val whiteColor = ContextCompat.getColor(requireContext(), R.color.white) // Oq rang
         val lightGrayColor = ContextCompat.getColor(requireContext(), R.color.light_gray) // Oq rang

// Dastlabki ranglarni o'rnatish va rejimni o'rnatish
         if (isNightMode) {
            dayNightButton.thumbTintList = ColorStateList.valueOf(whiteColor) // Oq rang
            dayNightButton.trackTintList = ColorStateList.valueOf(greenColor) // Yashil rang
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
         } else {
            dayNightButton.thumbTintList = ColorStateList.valueOf(whiteColor) // Oq rang
            dayNightButton.trackTintList = ColorStateList.valueOf(lightGrayColor) // Oq rang
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
         }

// Switch o'zgarishini kuzatish
         dayNightButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
               // Tungi rejimga o'tish
               AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
               dayNightButton.thumbTintList = ColorStateList.valueOf(whiteColor)
               dayNightButton.trackTintList = ColorStateList.valueOf(greenColor)
               editor.putBoolean("night_mode", true)
            } else {
               // Kunduzgi rejimga o'tish
               AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
               dayNightButton.thumbTintList = ColorStateList.valueOf(whiteColor)
               dayNightButton.trackTintList = ColorStateList.valueOf(lightGrayColor)
               editor.putBoolean("night_mode", false)
            }

            // Holatni saqlash va faoliyatni yangilash
            editor.apply()
            requireActivity().recreate()
         }

      }
   }
   private fun notificationButtonFun() {
      binding.apply {
         val isNightMode = (requireActivity() as MainActivity).loadNotificationPreference() // Holatni olish

         val greenColor = ContextCompat.getColor(requireContext(), R.color.green)
         val whiteColor = ContextCompat.getColor(requireContext(), R.color.white)
         val lightGrayColor = ContextCompat.getColor(requireContext(), R.color.light_gray)

         // Tugmani dastlabki holatda rang bilan sozlash
         notificationButton.apply {
            isChecked = isNightMode
            thumbTintList = ColorStateList.valueOf(whiteColor)
            trackTintList = ColorStateList.valueOf(if (isNightMode) greenColor else lightGrayColor)

            // Tugma holatini o'zgartirganda ranglarni yangilash
            setOnCheckedChangeListener { _, isChecked ->
               requireContext().getSharedPreferences("notification", Context.MODE_PRIVATE)
                  .edit()
                  .putBoolean("notification_mode", isChecked)
                  .apply()

               thumbTintList = ColorStateList.valueOf(whiteColor)
               trackTintList = ColorStateList.valueOf(if (isChecked) greenColor else lightGrayColor)
            }
         }
      }
   }


}