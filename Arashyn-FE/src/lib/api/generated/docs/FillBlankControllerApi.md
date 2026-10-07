# FillBlankControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**create8**](#create8) | **POST** /learning/fill_blank | |
|[**submit**](#submit) | **POST** /learning/fill_blank/submit | |

# **create8**
> CreateFillBlankResponse create8(createFillBlankTestRequest)


### Example

```typescript
import {
    FillBlankControllerApi,
    Configuration,
    CreateFillBlankTestRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new FillBlankControllerApi(configuration);

let createFillBlankTestRequest: CreateFillBlankTestRequest; //

const { status, data } = await apiInstance.create8(
    createFillBlankTestRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **createFillBlankTestRequest** | **CreateFillBlankTestRequest**|  | |


### Return type

**CreateFillBlankResponse**

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

# **submit**
> SubmitFillBlankResponse submit(submitFillBlankRequest)


### Example

```typescript
import {
    FillBlankControllerApi,
    Configuration,
    SubmitFillBlankRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new FillBlankControllerApi(configuration);

let submitFillBlankRequest: SubmitFillBlankRequest; //

const { status, data } = await apiInstance.submit(
    submitFillBlankRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **submitFillBlankRequest** | **SubmitFillBlankRequest**|  | |


### Return type

**SubmitFillBlankResponse**

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

