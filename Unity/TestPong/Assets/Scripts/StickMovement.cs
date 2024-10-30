using System.Collections;
using System.Collections.Generic;
using Unity.VisualScripting;
using UnityEngine;

//Monobehaviour nos indica que esto es un componente de Unity
public class StickMovement : MonoBehaviour
{
    public float speed;
    private Rigidbody2D rb2d;
    private float y;
    public string axisName;

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
        y = Input.GetAxis(axisName);
        transform.Translate(new Vector2 (0,y) * speed * Time.deltaTime);
        //Esto sería para que al chocar la bola con la pala no nos la mueva en el eje Y
    }

    private void FixedUpdate()
    {
        //Con esta función interactuamos con el rigidBody
        //rb2d.velocity = new Vector2(0, y) * speed;
    }
}
