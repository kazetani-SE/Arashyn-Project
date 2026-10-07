# MeaningControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**create11**](#create11) | **POST** /grammar/{grammarId}/meanings | |

# **create11**
> create11(meaningCreateRequest)


### Example

```typescript
import {
    MeaningControllerApi,
    Configuration,
    MeaningCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new MeaningControllerApi(configuration);

let grammarId: string; // (default to undefined)
let meaningCreateRequest: MeaningCreateRequest; //

const { status, data } = await apiInstance.create11(
    grammarId,
    meaningCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **meaningCreateRequest** | **MeaningCreateRequest**|  | |
| **grammarId** | [**string**] |  | defaults to undefined|


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

