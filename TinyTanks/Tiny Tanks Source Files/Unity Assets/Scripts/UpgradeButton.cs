// This code is used to simulate buttons that are used to upgrade components of the player tank, such as shot delay and tank movement speed

using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class UpgradeButton : MonoBehaviour
{

    public string upgradeType;
    PlayerTank player;
    public int[] upgradeCosts;
    public GameObject[] upgradeCircles;
    public int upgradeCounter = 0;
    public Sprite redCircle;
    public Sprite grayCircle;
    public GameObject upgradeButton;
    public Sprite canBuyButton;
    public Sprite cannotBuyButton;
    public TMP_Text costText;
    public string upgradeSound;

    void Awake()
    {
        costText.text = ("Cost: " + upgradeCosts[upgradeCounter]);
        player = FindObjectOfType<PlayerTank>();
    }

    void Update()
    {

        if (upgradeCounter == 10)
        {
            upgradeButton.SetActive(false);
            costText.text = "Max Level";
        }

        if (upgradeCosts[upgradeCounter] > player.getCurrentPoints())
        {
            upgradeButton.GetComponent<Image>().sprite = cannotBuyButton;
        }
        else
        {
            upgradeButton.GetComponent<Image>().sprite = canBuyButton;
        }

    }

    public void Upgrade()
    {
        if (upgradeCosts[upgradeCounter] <= player.getCurrentPoints())
        {
            FindObjectOfType<AudioManager>().Play(upgradeSound);
            player.removePoints(upgradeCosts[upgradeCounter]);
            upgradeCircles[upgradeCounter].gameObject.GetComponent<Image>().sprite = redCircle;
            upgradeCounter++;
            costText.text = ("Cost: " + upgradeCosts[upgradeCounter]);
            if (upgradeType == "Firerate")
            {
                player.reduceShotDelay(0.01f);
            }
            else if (upgradeType == "Bulletspeed")
            {
                player.increaseBulletSpeed(10f);
            }
            else if (upgradeType == "Bulletspread")
            {
                player.reduceBulletSpread(0.3f);
            }
            else if (upgradeType == "Tankspeed")
            {
                player.increaseTankSpeed(1000f);
            }

        }

    }

}
