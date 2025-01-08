using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CircleShot : MonoBehaviour
{
    public GameObject bulletPrefab;
    private Camera cam;
    public int numberOfBullets = 12;
    public GameObjectPool bulletPool;
    // Start is called before the first frame update
    void Start()
    {
        cam = Camera.main;
    }

    // Update is called once per frame
    void Update()
    {
        if (Input.GetMouseButtonDown(0))
        {
            Vector2 screenCoords = Input.mousePosition;
            Vector3 gameCoords = cam.ScreenToWorldPoint(screenCoords);
            gameCoords.z = 0;

            float step = 360f / numberOfBullets;

            for (int i = 0; i <numberOfBullets; i++)
            {
                float angle = step * i;
                Vector2 direction = new Vector2(Mathf.Cos(angle * Mathf.Deg2Rad), Mathf.Sin(angle * Mathf.Deg2Rad));

                GameObject o = bulletPool.getAvilableObject();
                o.transform.position = gameCoords;
                o.SetActive(true);

                o.GetComponent<Bullet>().setDirection(direction);
            }
            
        }
    }
}
