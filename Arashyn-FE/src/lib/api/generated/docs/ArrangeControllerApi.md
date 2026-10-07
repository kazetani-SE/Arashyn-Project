# ArrangeControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**create9**](#create9) | **POST** /learning/arrange | |
|[**submit1**](#submit1) | **POST** /learning/arrange/submit | |

# **create9**
> CreateArrangeResponse create9(createArrangeRequest)


### Example

```typescript
import {
    ArrangeControllerApi,
    Configuration,
    CreateArrangeRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new ArrangeControllerApi(configuration);

let createArrangeRequest: CreateArrangeRequest; //

const { status, data } = await apiInstance.create9(
    createArrangeRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **createArrangeRequest** | **CreateArrangeRequest**|  | |


### Return type

**CreateArrangeResponse**

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

# **submit1**
> SubmitArrangeResponse submit1(submitArrangeRequest)


### Example

```typescript
import {
    ArrangeControllerApi,
    Configuration,
    SubmitArrangeRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new ArrangeControllerApi(configuration);

let submitArrangeRequest: SubmitArrangeRequest; //

const { status, data } = await apiInstance.submit1(
    submitArrangeRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **submitArrangeRequest** | **SubmitArrangeRequest**|  | |


### Return type

**SubmitArrangeResponse**

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

