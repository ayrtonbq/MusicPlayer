package com.example.playermusic

import android.os.Handler
import android.os.Looper
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.playermusic.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isPlaying = false  // Variável para controlar o estado de play/pause
    private var musicDuration = 100 // Duração total da música em segundos (exemplo)
    private var currentPosition = 0 // Posição atual da música
    private lateinit var progressUpdateHandler: Handler // Handler para atualizar o SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflando o layout usando View Binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializando o SeekBar
        binding.seekBarProgress.max = musicDuration

        // Implementando eventos dos botões
        binding.btnPlayPause.setOnClickListener {
            if (isPlaying) {
                pauseMusic()  // Método para pausar a música
            } else {
                playMusic()   // Método para iniciar a música
            }
            isPlaying = !isPlaying  // Alterna o estado de play/pause
        }

        // Lógica para Stop
        binding.btnStop.setOnClickListener {
            stopMusic()  // Método para parar a música
        }

        // Navegação para a tela de configurações (SettingsActivity)
        binding.btnConfig.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        // Atualizando o SeekBar com um Handler
        progressUpdateHandler = Handler(Looper.getMainLooper())
    }

    // Método para iniciar a música
    private fun playMusic() {
        binding.btnPlayPause.text = "Pause"  // Alterar o texto do botão para "Pause"
        Toast.makeText(this, "Música tocando", Toast.LENGTH_SHORT).show()
        updateSeekBar() // Inicia a atualização do SeekBar
    }

    // Método para pausar a música
    private fun pauseMusic() {
        binding.btnPlayPause.text = "Play"  // Alterar o texto do botão para "Play"
        Toast.makeText(this, "Música pausada", Toast.LENGTH_SHORT).show()
        progressUpdateHandler.removeCallbacksAndMessages(null) // Para a atualização do SeekBar
    }

    // Método para parar a música
    private fun stopMusic() {
        isPlaying = false
        binding.btnPlayPause.text = "Play"  // Alterar o texto do botão para "Play"
        currentPosition = 0 // Reseta a posição
        binding.seekBarProgress.progress = currentPosition // Reseta o SeekBar
        Toast.makeText(this, "Música parada", Toast.LENGTH_SHORT).show()
        progressUpdateHandler.removeCallbacksAndMessages(null) // Para a atualização do SeekBar
    }

    // Método para atualizar o SeekBar
    private fun updateSeekBar() {
        progressUpdateHandler.postDelayed(object : Runnable {
            override fun run() {
                if (isPlaying && currentPosition < musicDuration) {
                    currentPosition++ // Incrementa a posição atual
                    binding.seekBarProgress.progress = currentPosition // Atualiza o SeekBar
                    progressUpdateHandler.postDelayed(this, 1000) // Chama novamente após 1 segundo
                }
            }
        }, 1000) // Inicia após 1 segundo
    }
}


