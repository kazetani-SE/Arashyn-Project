# ExampleControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**create7**](#create7) | **POST** /meanings/{meaningId}/examples | |

# **create7**
> create7(exampleCreateRequest)


### Example

```typescript
import {
    ExampleControllerApi,
    Configuration,
    ExampleCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new ExampleControllerApi(configuration);

let meaningId: string; // (default to undefined)
let exampleCreateRequest: ExampleCreateRequest; //

const { status, data } = await apiInstance.create7(
    meaningId,
    exampleCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **exampleCreateRequest** | **ExampleCreateRequest**|  | |
| **meaningId** | [**string**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

