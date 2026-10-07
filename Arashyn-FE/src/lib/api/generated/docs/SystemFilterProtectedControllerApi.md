# SystemFilterProtectedControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**create4**](#create4) | **POST** /protected/system-filters | |

# **create4**
> string create4(systemFilterCreateRequest)


### Example

```typescript
import {
    SystemFilterProtectedControllerApi,
    Configuration,
    SystemFilterCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new SystemFilterProtectedControllerApi(configuration);

let systemFilterCreateRequest: SystemFilterCreateRequest; //

const { status, data } = await apiInstance.create4(
    systemFilterCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **systemFilterCreateRequest** | **SystemFilterCreateRequest**|  | |


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

