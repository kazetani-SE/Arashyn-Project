# NoteControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**create10**](#create10) | **POST** /grammar/{grammarId}/notes | |

# **create10**
> create10(noteCreateRequest)


### Example

```typescript
import {
    NoteControllerApi,
    Configuration,
    NoteCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new NoteControllerApi(configuration);

let grammarId: string; // (default to undefined)
let noteCreateRequest: NoteCreateRequest; //

const { status, data } = await apiInstance.create10(
    grammarId,
    noteCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **noteCreateRequest** | **NoteCreateRequest**|  | |
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

