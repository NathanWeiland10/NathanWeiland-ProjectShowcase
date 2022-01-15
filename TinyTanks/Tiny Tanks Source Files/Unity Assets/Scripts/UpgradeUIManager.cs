// This code is used to simulate the upgrade UI screen and tells other scripts / the game when the upgrade screen is up

using UnityEngine;

public class UpgradeUIManager : MonoBehaviour
{
    bool isUpgradingScreen = false;
    public GameObject upgradeScreen;
    GameManager gameManager;

    void Awake()
    {
        gameManager = FindObjectOfType<GameManager>();
        upgradeScreen.SetActive(false);
    }
    void Update()
    {
        if (Input.GetKeyDown(KeyCode.Space) && !gameManager.isOnPauseScreen())
        {
            if (!isUpgradingScreen)
            {
                LoadUpgradeScreen();
            }
            else
            {
                UnloadUpgradeScreen();
            }
        }

    }
    public void LoadUpgradeScreen()
    {
        upgradeScreen.SetActive(true);
        Time.timeScale = 0f;
        isUpgradingScreen = true;
    }
    public void UnloadUpgradeScreen()
    {
        upgradeScreen.SetActive(false);
        Time.timeScale = 1f;
        isUpgradingScreen = false;
    }

    public bool isOnUpgradeScreen()
    {
        return isUpgradingScreen;
    }

}
