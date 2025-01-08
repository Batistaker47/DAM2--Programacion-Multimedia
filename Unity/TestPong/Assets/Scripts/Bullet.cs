using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour
{
    private Rigidbody2D rb2d;
    private Vector2 direction = Vector2.right;
    public float speed, timeAlive;
    private float currentTime = 0;
    // Start is called before the first frame update
    void Start()
    {
        rb2d = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame
    void Update()
    {
        currentTime += Time.deltaTime;
        if (currentTime >= timeAlive)
        {
            currentTime = 0;
            gameObject.SetActive(false);
        }

    }
    void FixedUpdate()
    {
        if (rb2d != null) { 
            rb2d.velocity = direction * speed; 
        }
    }
    public void setDirection(Vector2 value)
    {
        direction = value;
    }
}
