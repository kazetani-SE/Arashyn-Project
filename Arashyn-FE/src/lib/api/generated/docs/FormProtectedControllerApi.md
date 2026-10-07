# FormProtectedControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**create6**](#create6) | **POST** /protected/forms | |

# **create6**
> string create6(formCreateRequest)


### Example

```typescript
import {
    FormProtectedControllerApi,
    Configuration,
    FormCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new FormProtectedControllerApi(configuration);

let formCreateRequest: FormCreateRequest; //

const { status, data } = await apiInstance.create6(
    formCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **formCreateRequest** | **FormCreateRequest**|  | |


### Return type

**string**

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: */*


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

