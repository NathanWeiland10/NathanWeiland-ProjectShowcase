// This code is used to allow the player to purchase a minitank and instantiate into the game

using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class FriendlyTankUpgrade : MonoBehaviour
{
    PlayerTank player;
    public int upgradeCost;
    public GameObject upgradeButton;
    public Sprite canBuyButton;
    public Sprite cannotBuyButton;
    public TMP_Text costText;
    public string upgradeSound;

    public GameObject tankToSpawn;
    public Vector3 tankSpawnLocation;

    void Awake()
    {
        costText.text = ("Cost: " + upgradeCost);
        player = FindObjectOfType<PlayerTank>();
    }

    void Update()
    {

        if (upgradeCost > player.getCurrentPoints())
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
        if (upgradeCost <= player.getCurrentPoints())
        {

            float randX = Random.Range(-8f, 8f);
            float randY = Random.Range(1, 5f);
            Vector3 spawnLocation = new Vector3(tankSpawnLocation.x + randX, tankSpawnLocation.y + randY, tankSpawnLocation.z);
            FindObjectOfType<AudioManager>().Play(upgradeSound);
            player.removePoints(upgradeCost);
            GameObject spawnedTank = Instantiate(tankToSpawn, spawnLocation, Quaternion.identity);
        }


    }
}
