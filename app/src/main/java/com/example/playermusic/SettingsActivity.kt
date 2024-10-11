package com.example.playermusic

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.playermusic.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializando SharedPreferences
        sharedPreferences = getSharedPreferences("PlayerMusicPrefs", MODE_PRIVATE)

        // Carregar configurações salvas
        loadSettings()

        // Lógica para o SeekBar de velocidade de reprodução
        binding.seekBarSpeed.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // Atualiza o texto de velocidade
                binding.textViewSpeed.text = "${progress}%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        // Lógica para aplicar as configurações
        binding.btnApplySettings.setOnClickListener {
            applySettings()
            finish() // Fecha a tela de configurações e volta para a principal
        }
    }

    private fun loadSettings() {
        // Carregar e aplicar as configurações
        binding.checkboxShuffle.isChecked = sharedPreferences.getBoolean("shuffle", false)
        binding.checkboxRepeat.isChecked = sharedPreferences.getBoolean("repeat", false)
        binding.seekBarSpeed.progress = sharedPreferences.getInt("speed", 100)
        binding.textViewSpeed.text = "${binding.seekBarSpeed.progress}%"
        val theme = sharedPreferences.getString("theme", "light")
        if (theme == "dark") {
            binding.radioButtonDark.isChecked = true
        } else {
            binding.radioButtonLight.isChecked = true
        }
    }

    private fun applySettings() {
        // Salvar configurações
        val editor = sharedPreferences.edit()
        editor.putBoolean("shuffle", binding.checkboxShuffle.isChecked)
        editor.putBoolean("repeat", binding.checkboxRepeat.isChecked)
        editor.putInt("speed", binding.seekBarSpeed.progress)

        // Salvar o tema
        val selectedTheme = if (binding.radioButtonDark.isChecked) "dark" else "light"
        editor.putString("theme", selectedTheme)
        editor.apply()

        Toast.makeText(this, "Configurações aplicadas!", Toast.LENGTH_SHORT).show()
    }
}
