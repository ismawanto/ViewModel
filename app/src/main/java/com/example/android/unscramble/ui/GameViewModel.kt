package com.example.android.unscramble.ui
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.android.unscramble.ui.game.MAX_NO_OF_WORDS
import com.example.android.unscramble.ui.game.SCORE_INCREASE
import com.example.android.unscramble.ui.game.allWordsList

/**
 * ViewModel yang berisi data aplikasi dan metode untuk memproses data
*/
class GameViewModel : ViewModel(){
    private var _score = 0
    val score: Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    // Untuk menamapilkan daftar kata yang akan digunakan.
    private var wordsList: MutableList<String> = mutableListOf()
    private lateinit var currentWord: String

    init {
        Log.d("GameFragment", "GameViewModel created!")
        getNextWord()
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("GameFragment", "GameViewModel destroyed!")
    }

    /*
    * Untuk memperbarui word saat berjalan dan saat aplikasi dengan menggunakan kata-kata berikutnya.
    */
    private fun getNextWord() {
        currentWord = allWordsList.random()
        val tempWord = currentWord.toCharArray()
        tempWord.shuffle()

        while (String(tempWord).equals(currentWord, false)) {
            tempWord.shuffle()
        }
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            _currentScrambledWord = String(tempWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    /*
    *Untuk melakukan inisialisasi ulang data game untuk memulai ulang game.
    */
    fun reinitializeData() {
        _score = 0
        _currentWordCount = 0
        wordsList.clear()
        getNextWord()
    }

    /*
    * Untuk meningkatkan skor pemain apabila jawaban dengan kata-kata benar.
    */
    private fun increaseScore() {
        _score += SCORE_INCREASE
    }

    /*
    * Untuk mengembalikan nilai benar jika kata pemain menjawab dengan benar.
    * Untuk meningkatkan skor yang telah disesuaikan.
    */
    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    /*
    * Untuk mengembalikan nilai benar jika jumlah kata yang digunakan saat ini kurang dari yang telah disediakan.
    */
    fun nextWord(): Boolean {
        return if (_currentWordCount < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }
}