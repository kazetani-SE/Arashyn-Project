# FormPublicControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**findByLanguage**](#findbylanguage) | **GET** /public/forms | |

# **findByLanguage**
> ListFormResponse findByLanguage()


### Example

```typescript
import {
    FormPublicControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new FormPublicControllerApi(configuration);

let language: 'VI' | 'EN' | 'JA' | 'KO' | 'ZH'; // (default to undefined)

const { status, data } = await apiInstance.findByLanguage(
    language
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **language** | [**&#39;VI&#39; | &#39;EN&#39; | &#39;JA&#39; | &#39;KO&#39; | &#39;ZH&#39;**]**Array<&#39;VI&#39; &#124; &#39;EN&#39; &#124; &#39;JA&#39; &#124; &#39;KO&#39; &#124; &#39;ZH&#39;>** |  | defaults to undefined|


### Return type

**ListFormResponse**

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

