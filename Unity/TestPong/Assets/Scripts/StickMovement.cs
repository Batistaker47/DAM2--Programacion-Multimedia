using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;

//Monobehaviour nos indica que esto es un componente de Unity
public class StickMovement : MonoBehaviour
{
    public float speed;
    private Rigidbody2D rb2d;
    private float x, y;

    // Start is called before the first frame update.
    // Este método es virtual, lo que significa que lo heredamos de monoBehaviour vacío, y lo rellenaremos con lo que necesitemos
    void Start()
    {
        //Con esto accedemos a las propiedades del componente Rigidbody2D
        rb2d = GetComponent<Rigidbody2D>();
    }

    // Update is called once per frame
    void Update()
    {
        x = Input.GetAxis("Horizontal");
        y = Input.GetAxis("Vertical");
    }

    private void FixedUpdate()
    {
        //Con esta función interactuamos con el rididBody
        rb2d.velocity = new Vector2(x, y) * speed;
    }
}
