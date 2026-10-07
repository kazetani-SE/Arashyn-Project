# ProficiencyControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**update5**](#update5) | **PATCH** /learning/proficiency | |

# **update5**
> update5(updateProficiencyRequest)


### Example

```typescript
import {
    ProficiencyControllerApi,
    Configuration,
    UpdateProficiencyRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new ProficiencyControllerApi(configuration);

let updateProficiencyRequest: UpdateProficiencyRequest; //

const { status, data } = await apiInstance.update5(
    updateProficiencyRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **updateProficiencyRequest** | **UpdateProficiencyRequest**|  | |


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

