using System;
using UnityEngine;

public class Ball : MonoBehaviour
{
    public float speed;
    private Rigidbody2D rb;
    private Vector2 direction;
    private Vector2 initialPosition;

    // Start is called before the first frame update
    void Start()
    {
        rb = GetComponent<Rigidbody2D>();

        do
        {
            direction.x = UnityEngine.Random.Range(-1, 2);
        } 
        while (direction.x == 0);

        direction.y = UnityEngine.Random.Range(-1, 2);

        initialPosition = transform.position;

    }

    // Update is called once per frame
    void Update()
    {
        
    }

    // collision es el objeto con el que te has chocado
    private void OnCollisionEnter2D(Collision2D collision)
    {
        // Cada vez que ocurre una collision CON LA PALA, la bola rebota

        if (collision.gameObject.GetComponent<StickMovement>())
        {
            direction.x *= -1;
            direction.y = UnityEngine.Random.Range(-1, 2);
            
            // Collision con CUALQUIER OTRA COSA
        } else 
        {
            // Como solo hay techo o suelo
            direction.y *= -1;
        }
    }
    public void ResetPosition()
    {
        transform.position = initialPosition;
    }

    private void FixedUpdate()
    {
        rb.velocity = direction * speed;
    }
}
