// This script is used to simulate the how to play screen by changing slides with a forward and backward button

using UnityEngine;

public class HowToPlayMenu : MonoBehaviour
{
    public int slideCounter = 1;
    public GameObject slide1;
    public GameObject slide2;
    public GameObject slide3;
    public GameObject slide4;
    public GameObject nextButton;
    public GameObject backButton;
    public GameObject mainMenu;

    void Update()
    {
        switch (slideCounter)
        {
            case 0:
                slide1.SetActive(false);
                slide2.SetActive(false);
                slide3.SetActive(false);
                slide4.SetActive(false);
                nextButton.SetActive(false);
                backButton.SetActive(false);
                break;
            case 1:
                slide1.SetActive(true);
                slide2.SetActive(false);
                slide3.SetActive(false);
                slide4.SetActive(false);
                mainMenu.SetActive(false);
                nextButton.SetActive(true);
                backButton.SetActive(true);
                break;
            case 2:
                slide1.SetActive(false);
                slide2.SetActive(true);
                slide3.SetActive(false);
                slide4.SetActive(false);
                mainMenu.SetActive(false);
                nextButton.SetActive(true);
                backButton.SetActive(true);
                break;
            case 3:
                slide1.SetActive(false);
                slide2.SetActive(false);
                slide3.SetActive(true);
                slide4.SetActive(false);
                mainMenu.SetActive(false);
                nextButton.SetActive(true);
                backButton.SetActive(true);
                break;
            case 4:
                slide1.SetActive(false);
                slide2.SetActive(false);
                slide3.SetActive(false);
                slide4.SetActive(true);
                mainMenu.SetActive(false);
                nextButton.SetActive(true);
                backButton.SetActive(true);
                break;
            case 5:
                slide1.SetActive(false);
                slide2.SetActive(false);
                slide3.SetActive(false);
                slide4.SetActive(false);
                nextButton.SetActive(false);
                backButton.SetActive(false);
                break;
        }
    }

    public void changeSlide(int amount)
    {
        slideCounter += amount;
        if (slideCounter >= 5 || slideCounter <= 0)
        {
            mainMenu.SetActive(true);
        }
    }

    public void setSlide(int slide)
    {
        slideCounter = slide;
        if (slideCounter >= 5 || slideCounter <= 0)
        {
            mainMenu.SetActive(true);
        }
    }

}
