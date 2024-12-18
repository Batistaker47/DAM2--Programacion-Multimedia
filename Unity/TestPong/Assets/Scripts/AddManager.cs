using System.Collections;
using System.Collections.Generic;
using System.Diagnostics;
using UnityEngine;
using UnityEngine.Advertisements;

public class AddManager : MonoBehaviour, IUnityAdsInitializationListener , IUnityAdsLoadListener, IUnityAdsShowListener
{

    public static AddManager instance;
    public string androidID, appleID;
    public bool testMode = true;

    public void OnInitializationComplete()
    {
        throw new System.NotImplementedException();
    }

    public void OnInitializationFailed(UnityAdsInitializationError error, string message)
    {
        throw new System.NotImplementedException();
    }

    public void OnUnityAdsAdLoaded(string placementId)
    {
        Advertisement.Show("Interstitial_Android", this);
    }

    public void OnUnityAdsFailedToLoad(string placementId, UnityAdsLoadError error, string message)
    {
        throw new System.NotImplementedException();
    }

    public void OnUnityAdsShowClick(string placementId)
    {
        throw new System.NotImplementedException();
    }

    public void OnUnityAdsShowComplete(string placementId, UnityAdsShowCompletionState showCompletionState)
    {
        throw new System.NotImplementedException();
    }

    public void OnUnityAdsShowFailure(string placementId, UnityAdsShowError error, string message)
    {
        throw new System.NotImplementedException();
    }

    public void OnUnityAdsShowStart(string placementId)
    {
        throw new System.NotImplementedException();
    }

    private void Awake()
    {
        if(!instance)
        {
            instance = this;
            DontDestroyOnLoad(gameObject);
        } else
        {
            Destroy(gameObject);
        }
    }
    // Start is called before the first frame update
    void Start()
    {
        if (!Advertisement.isInitialized)
        {
#if UNITY_EDITOR || UNITY_STANDALONE || UNITY_ANDROID
            Advertisement.Initialize(androidID, testMode, this);
#elif UNITY_IOS
            Advertisement.Initialize(appleID, testMode, this);
#endif
        }
    }

    // Update is called once per frame
    void Update()
    {
        
    }
    public void ShowAd()
    {
        if(Advertisement.isInitialized)
        {
            Advertisement.Load("Interstitial_Android",this);
        }
    }
}
