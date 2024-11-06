using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;

public class UpdateScore : MonoBehaviour
{
    private TMP_Text textComponent;
    public uint playerIndex;
    // Start is called before the first frame update
    void Start()
    {
        // Accede a la lista de componentes del objeto al que llamas. Si tiene ese componente, te lo guarda en la variable 
        textComponent = GetComponent<TMP_Text>();
    }

    // Update is called once per frame
    void Update()
    {
        textComponent.text = "Jugador" + playerIndex + ": " + GameManager.instance.GetIndexScore((int)playerIndex);
    }
}
