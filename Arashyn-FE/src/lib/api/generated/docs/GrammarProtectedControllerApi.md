# GrammarProtectedControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**assignFilters**](#assignfilters) | **POST** /protected/grammar/{grammarId}/filters | |
|[**create5**](#create5) | **POST** /protected/grammar/create | |
|[**createMultiple**](#createmultiple) | **POST** /protected/grammar/create_multiple | |
|[**deleteGrammar**](#deletegrammar) | **DELETE** /protected/grammar/{grammarId} | |
|[**extendGrammar**](#extendgrammar) | **POST** /protected/grammar/{grammarId}/extend | |
|[**getUpdateDetail**](#getupdatedetail) | **GET** /protected/grammar/edit/{grammarId} | |
|[**restoreGrammar**](#restoregrammar) | **POST** /protected/grammar/{grammarId}/restore | |
|[**update4**](#update4) | **POST** /protected/grammar/update | |

# **assignFilters**
> assignFilters(assignFilterRequest)


### Example

```typescript
import {
    GrammarProtectedControllerApi,
    Configuration,
    AssignFilterRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarProtectedControllerApi(configuration);

let grammarId: string; // (default to undefined)
let assignFilterRequest: AssignFilterRequest; //

const { status, data } = await apiInstance.assignFilters(
    grammarId,
    assignFilterRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **assignFilterRequest** | **AssignFilterRequest**|  | |
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

# **create5**
> GrammarCreateResponse create5(grammarCreateRequest)


### Example

```typescript
import {
    GrammarProtectedControllerApi,
    Configuration,
    GrammarCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarProtectedControllerApi(configuration);

let grammarCreateRequest: GrammarCreateRequest; //

const { status, data } = await apiInstance.create5(
    grammarCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarCreateRequest** | **GrammarCreateRequest**|  | |


### Return type

**GrammarCreateResponse**

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

# **createMultiple**
> createMultiple(grammarCreateMultipleRequest)


### Example

```typescript
import {
    GrammarProtectedControllerApi,
    Configuration,
    GrammarCreateMultipleRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarProtectedControllerApi(configuration);

let grammarCreateMultipleRequest: GrammarCreateMultipleRequest; //

const { status, data } = await apiInstance.createMultiple(
    grammarCreateMultipleRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarCreateMultipleRequest** | **GrammarCreateMultipleRequest**|  | |


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

# **deleteGrammar**
> deleteGrammar()


### Example

```typescript
import {
    GrammarProtectedControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarProtectedControllerApi(configuration);

let grammarId: string; // (default to undefined)

const { status, data } = await apiInstance.deleteGrammar(
    grammarId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarId** | [**string**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **extendGrammar**
> extendGrammar(grammarExtendRequest)


### Example

```typescript
import {
    GrammarProtectedControllerApi,
    Configuration,
    GrammarExtendRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarProtectedControllerApi(configuration);

let grammarId: string; // (default to undefined)
let grammarExtendRequest: GrammarExtendRequest; //

const { status, data } = await apiInstance.extendGrammar(
    grammarId,
    grammarExtendRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarExtendRequest** | **GrammarExtendRequest**|  | |
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

# **getUpdateDetail**
> GrammarEditResponse getUpdateDetail()


### Example

```typescript
import {
    GrammarProtectedControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarProtectedControllerApi(configuration);

let grammarId: string; // (default to undefined)

const { status, data } = await apiInstance.getUpdateDetail(
    grammarId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarId** | [**string**] |  | defaults to undefined|


### Return type

**GrammarEditResponse**

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

# **restoreGrammar**
> restoreGrammar()


### Example

```typescript
import {
    GrammarProtectedControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarProtectedControllerApi(configuration);

let grammarId: string; // (default to undefined)

const { status, data } = await apiInstance.restoreGrammar(
    grammarId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarId** | [**string**] |  | defaults to undefined|


### Return type

void (empty response body)

### Authorization

[BearerAuth](../README.md#BearerAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: Not defined


### HTTP response details
| Status code | Description | Response headers |
|-------------|-------------|------------------|
|**200** | OK |  -  |

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **update4**
> update4(grammarUpdateRequest)


### Example

```typescript
import {
    GrammarProtectedControllerApi,
    Configuration,
    GrammarUpdateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarProtectedControllerApi(configuration);

let grammarUpdateRequest: GrammarUpdateRequest; //

const { status, data } = await apiInstance.update4(
    grammarUpdateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarUpdateRequest** | **GrammarUpdateRequest**|  | |


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

