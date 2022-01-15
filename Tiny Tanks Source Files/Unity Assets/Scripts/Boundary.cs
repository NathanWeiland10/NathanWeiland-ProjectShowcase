// This code is based off of a tutorial from Random Art Attack (https://www.youtube.com/watch?v=8iW2GbWlFWE&t=35s) and is used to place boundaries so that the player will not move outside of the screen

using UnityEngine;

public class Boundary : MonoBehaviour
{
    public Camera cam;
    float width;
    float height;
    public EdgeCollider2D edge;

    private void Awake()
    {
        edge = GetComponent<EdgeCollider2D>();
    }

    private void Update()
    {
        FindBoundaries();
        SetBoundaries();
    }

    void SetBoundaries()
    {
        Vector2 pointa = new Vector2(width / 2, height /2);
        Vector2 pointb = new Vector2(width / 2, -height / 2);
        Vector2 pointc = new Vector2(-width / 2, -height / 2);
        Vector2 pointd = new Vector2(-width / 2, height / 2);
        Vector2[] tempArray = new Vector2[] {pointa, pointb, pointc, pointd, pointa };
        edge.points = tempArray;
    }

    void FindBoundaries()
    {
        width = 1 / (cam.WorldToViewportPoint(new Vector3(1, 1, -52)).x - 0.5f);
        height = 1 / (cam.WorldToViewportPoint(new Vector3(1, 1, -52)).y - 0.5f);
    }

}
