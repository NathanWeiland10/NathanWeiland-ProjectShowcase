// This code is used to allow the player to upgrade the barrel of the player tank and allows the player to swap between the barrels they have purchased

using UnityEngine;
using UnityEngine.UI;
using TMPro;

public class BarrelUpgrade : MonoBehaviour
{
    public string upgradeSound;
    PlayerTank player;
    public Sprite inUseButton;
    public Sprite notInUseButton;
    public Sprite XButton;
    public int curBar = 1;

    public GameObject standBarButton;
    public TMP_Text standBarText;
    public GameObject doubleBarButton;
    public TMP_Text doubleBarText;
    public GameObject splitBarButton;
    public TMP_Text splitBarText;
    public GameObject minigunBarButton;
    public TMP_Text minigunBarText;

    public int doubleBarCost;
    public int splitBarCost;
    public int minigunCost;

    bool hasDouble = false;
    bool hasSplit = false;
    bool hasMinigun = false;

    int upgradeCost;

    void Awake()
    {
        player = FindObjectOfType<PlayerTank>();
    }

    void Update()
    {
        switch (curBar)
        {
            case 1:
                standBarButton.GetComponent<Image>().sprite = inUseButton;
                standBarText.text = "Currently\nin Use";
                if (hasDouble)
                {
                    doubleBarButton.GetComponent<Image>().sprite = notInUseButton;
                    doubleBarText.text = "Click\nto Use";
                }
                else
                {
                    doubleBarButton.GetComponent<Image>().sprite = XButton;
                    doubleBarText.text = "Cost: " + doubleBarCost;
                }
                if (hasSplit)
                {
                    splitBarButton.GetComponent<Image>().sprite = notInUseButton;
                    splitBarText.text = "Click\nto Use";
                }
                else
                {
                    splitBarButton.GetComponent<Image>().sprite = XButton;
                    splitBarText.text = "Cost: " + splitBarCost;
                }
                if (hasMinigun)
                {
                    minigunBarButton.GetComponent<Image>().sprite = notInUseButton;
                    minigunBarText.text = "Click\nto Use";
                }
                else
                {
                    minigunBarButton.GetComponent<Image>().sprite = XButton;
                    minigunBarText.text = "Cost: " + minigunCost;
                }
                break;
            case 2:
                doubleBarButton.GetComponent<Image>().sprite = inUseButton;
                doubleBarText.text = "Currently\nin use";
                standBarText.text = "Click\nto Use";
                standBarButton.GetComponent<Image>().sprite = notInUseButton;
                if (hasSplit)
                {
                    splitBarButton.GetComponent<Image>().sprite = notInUseButton;
                    splitBarText.text = "Click\nto Use";
                }
                else
                {
                    splitBarButton.GetComponent<Image>().sprite = XButton;
                    splitBarText.text = "Cost: " + splitBarCost;
                }
                if (hasMinigun)
                {
                    minigunBarButton.GetComponent<Image>().sprite = notInUseButton;
                    minigunBarText.text = "Click\nto Use";
                }
                else
                {
                    minigunBarButton.GetComponent<Image>().sprite = XButton;
                    minigunBarText.text = "Cost: " + minigunCost;
                }
                break;
            case 3:
                splitBarButton.GetComponent<Image>().sprite = inUseButton;
                splitBarText.text = "Currently\nin use";
                standBarText.text = "Click\nto Use";
                standBarButton.GetComponent<Image>().sprite = notInUseButton;
                if (hasDouble)
                {
                    doubleBarButton.GetComponent<Image>().sprite = notInUseButton;
                    doubleBarText.text = "Click\nto Use";
                }
                else
                {
                    doubleBarButton.GetComponent<Image>().sprite = XButton;
                    doubleBarText.text = "Cost: " + doubleBarCost;
                }
                if (hasMinigun)
                {
                    minigunBarButton.GetComponent<Image>().sprite = notInUseButton;
                    minigunBarText.text = "Click\nto Use";
                }
                else
                {
                    minigunBarButton.GetComponent<Image>().sprite = XButton;
                    minigunBarText.text = "Cost: " + minigunCost;
                }
                break;
            case 4:
                minigunBarButton.GetComponent<Image>().sprite = inUseButton;
                minigunBarText.text = "Currently\nin use";
                standBarText.text = "Click\nto Use";
                standBarButton.GetComponent<Image>().sprite = notInUseButton;
                if (hasDouble)
                {
                    doubleBarButton.GetComponent<Image>().sprite = notInUseButton;
                    doubleBarText.text = "Click\nto Use";
                }
                else
                {
                    doubleBarButton.GetComponent<Image>().sprite = XButton;
                    doubleBarText.text = "Cost: " + doubleBarCost;
                }
                if (hasSplit)
                {
                    splitBarButton.GetComponent<Image>().sprite = notInUseButton;
                    splitBarText.text = "Click\nto Use";
                }
                else
                {
                    splitBarButton.GetComponent<Image>().sprite = XButton;
                    splitBarText.text = "Cost: " + splitBarCost;
                }
                break;
        }
    }

    public void changeBarrel(int bar)
    {
        switch(bar) 
        {
            case 1:
                upgradeCost = 0;
                break;
            case 2:
                if (hasDouble)
                {
                    upgradeCost = 0;
                }
                else 
                {
                    upgradeCost = doubleBarCost;
                }
                break;
            case 3:
                if (hasSplit)
                {
                    upgradeCost = 0;
                }
                else
                {
                    upgradeCost = splitBarCost;
                }
                break;
            case 4:
                if (hasMinigun)
                {
                    upgradeCost = 0;
                }
                else
                {
                    upgradeCost = minigunCost;
                }
                break;
        }

        if (upgradeCost <= player.getCurrentPoints()) {

            FindObjectOfType<AudioManager>().Play(upgradeSound);
            player.removePoints(upgradeCost);

            curBar = bar;
            switch (bar)
            {
                case 2:
                    hasDouble = true;
                    break;
                case 3:
                    hasSplit = true;
                    break;
                case 4:
                    hasMinigun = true;
                    break;
            }
            player.changeBarrel(bar);
        }
    }

}
