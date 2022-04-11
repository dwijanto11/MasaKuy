package com.codenesia.masakuy.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.codenesia.masakuy.Utils.checkTheme
import com.codenesia.masakuy.database.Preferences
import com.codenesia.masakuy.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private lateinit var settingViewModel: SettingViewModel
    private var _binding: FragmentSettingBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingViewModel =
            ViewModelProvider(this).get(SettingViewModel::class.java)

        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        var preferences = Preferences(requireContext())
        val switch = binding.switch1
        switch.setOnClickListener({
            preferences.setThemeDark(switch.isChecked)
            checkTheme(preferences.isThemeDark())
        })
        switch.isChecked = preferences.isThemeDark()

        checkTheme(preferences.isThemeDark())
    }

        override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}