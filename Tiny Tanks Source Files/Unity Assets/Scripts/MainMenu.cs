// This code was created by Brackeyes (https://www.youtube.com/watch?v=zc8ac_qUXQY&t=455s) and is used to simulate the main menu of the game, allowing the player to play the game, quit the game, and open the options menu

using UnityEngine;
using UnityEngine.SceneManagement;

public class MainMenu : MonoBehaviour
{
    // Begin Citation:
    // From: https://www.youtube.com/watch?v=zc8ac_qUXQY&t=455s
    // This script is used to simulate the main menu of the game:

    public void PlayGame()
    {
        SceneManager.LoadScene("Level1");
        Time.timeScale = 1f;
    }

    public void LoadMainMenu()
    {
        SceneManager.LoadScene("MainMenu");
    }

    public void QuitGame()
    {
        Application.Quit();
    }
    // End Citation

}
