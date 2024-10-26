package uz.karsoft.baxt

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import org.koin.android.ext.android.inject
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
   }

   private fun setupRecyclerView() {
      settingsAdapter = SettingsAdapter(itemsList) { selectedItem ->
         when (selectedItem) {
            "Настройки" -> { findNavController().navigate(R.id.action_settingsFragment_to_nav_profile)}
            "Общие настройки" ->{
               itemsList.removeAt(1)
               settingsAdapter.notifyDataSetChanged()
               binding.constraintLyout1.visibility=View.VISIBLE
               binding.constraintLyout2.visibility=View.GONE
            }
            "Настройки интерфейса" ->{
               itemsList.removeAt(1)
               settingsAdapter.notifyDataSetChanged()
               binding.constraintLyout1.visibility=View.VISIBLE
               binding.constraintLyout2.visibility=View.GONE
               binding.constraintLyout3.visibility=View.GONE
            }
            "Прочие настройки" ->{
               itemsList.removeAt(1)
               settingsAdapter.notifyDataSetChanged()
               binding.constraintLyout1.visibility=View.VISIBLE
               binding.constraintLyout2.visibility=View.GONE
               binding.constraintLyout3.visibility=View.GONE
               binding.constraintLyout4.visibility=View.GONE
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
             constraintLyout1.visibility=View.GONE
             constraintLyout2.visibility=View.VISIBLE
          }
      }
      binding.cardView2.onClick {
         binding.apply {
            itemsList.add("Настройки интерфейса")
            settingsAdapter.notifyDataSetChanged()
            constraintLyout1.visibility=View.GONE
            constraintLyout2.visibility=View.GONE
            constraintLyout3.visibility=View.VISIBLE
         }
      }
      binding.cardView3.onClick {
         binding.apply {
            itemsList.add("Прочие настройки")
            settingsAdapter.notifyDataSetChanged()
            constraintLyout1.visibility=View.GONE
            constraintLyout2.visibility=View.GONE
            constraintLyout3.visibility=View.GONE
            constraintLyout4.visibility=View.VISIBLE
         }
      }
   }


}







