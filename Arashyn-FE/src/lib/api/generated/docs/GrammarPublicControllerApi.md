# GrammarPublicControllerApi

All URIs are relative to *http://localhost:8080*

|Method | HTTP request | Description|
|------------- | ------------- | -------------|
|[**checkGrammarExist**](#checkgrammarexist) | **POST** /public/grammar/check-exist | |
|[**checkSimilarGrammar**](#checksimilargrammar) | **POST** /public/grammar/similar | |
|[**getDetail**](#getdetail) | **GET** /public/grammar/{grammarId} | |
|[**getItems**](#getitems) | **GET** /public/grammar/item_list/grammar | |
|[**getPublicGrammars**](#getpublicgrammars) | **POST** /public/grammar | |
|[**search**](#search) | **GET** /public/grammar/search | |

# **checkGrammarExist**
> ExistingGrammarResponse checkGrammarExist(grammarCreateRequest)


### Example

```typescript
import {
    GrammarPublicControllerApi,
    Configuration,
    GrammarCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarPublicControllerApi(configuration);

let grammarCreateRequest: GrammarCreateRequest; //

const { status, data } = await apiInstance.checkGrammarExist(
    grammarCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarCreateRequest** | **GrammarCreateRequest**|  | |


### Return type

**ExistingGrammarResponse**

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

# **checkSimilarGrammar**
> GrammarSimilarResponse checkSimilarGrammar(grammarCreateRequest)


### Example

```typescript
import {
    GrammarPublicControllerApi,
    Configuration,
    GrammarCreateRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarPublicControllerApi(configuration);

let grammarCreateRequest: GrammarCreateRequest; //

const { status, data } = await apiInstance.checkSimilarGrammar(
    grammarCreateRequest
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarCreateRequest** | **GrammarCreateRequest**|  | |


### Return type

**GrammarSimilarResponse**

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

# **getDetail**
> GrammarDetailResponse getDetail()


### Example

```typescript
import {
    GrammarPublicControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarPublicControllerApi(configuration);

let grammarId: string; // (default to undefined)

const { status, data } = await apiInstance.getDetail(
    grammarId
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarId** | [**string**] |  | defaults to undefined|


### Return type

**GrammarDetailResponse**

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

# **getItems**
> GrammarListResponse getItems()


### Example

```typescript
import {
    GrammarPublicControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarPublicControllerApi(configuration);

let page: number; // (optional) (default to 0)
let language: string; // (optional) (default to 'VI')
let size: number; // (optional) (default to 20)
let sort: string; // (optional) (default to 'created_at')
let direction: string; // (optional) (default to 'desc')

const { status, data } = await apiInstance.getItems(
    page,
    language,
    size,
    sort,
    direction
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **page** | [**number**] |  | (optional) defaults to 0|
| **language** | [**string**] |  | (optional) defaults to 'VI'|
| **size** | [**number**] |  | (optional) defaults to 20|
| **sort** | [**string**] |  | (optional) defaults to 'created_at'|
| **direction** | [**string**] |  | (optional) defaults to 'desc'|


### Return type

**GrammarListResponse**

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

# **getPublicGrammars**
> GrammarListResponse getPublicGrammars(grammarListRequest)


### Example

```typescript
import {
    GrammarPublicControllerApi,
    Configuration,
    GrammarListRequest
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarPublicControllerApi(configuration);

let grammarListRequest: GrammarListRequest; //
let page: number; // (optional) (default to 0)

const { status, data } = await apiInstance.getPublicGrammars(
    grammarListRequest,
    page
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **grammarListRequest** | **GrammarListRequest**|  | |
| **page** | [**number**] |  | (optional) defaults to 0|


### Return type

**GrammarListResponse**

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

# **search**
> GrammarListResponse search()


### Example

```typescript
import {
    GrammarPublicControllerApi,
    Configuration
} from 'arashyn-api';

const configuration = new Configuration();
const apiInstance = new GrammarPublicControllerApi(configuration);

let query: string; // (optional) (default to '')
let filters: string; // (optional) (default to undefined)
let forms: string; // (optional) (default to undefined)
let isKeyword: boolean; // (optional) (default to true)
let page: number; // (optional) (default to 0)
let size: number; // (optional) (default to 20)
let language: string; // (optional) (default to 'VI')

const { status, data } = await apiInstance.search(
    query,
    filters,
    forms,
    isKeyword,
    page,
    size,
    language
);
```

### Parameters

|Name | Type | Description  | Notes|
|------------- | ------------- | ------------- | -------------|
| **query** | [**string**] |  | (optional) defaults to ''|
| **filters** | [**string**] |  | (optional) defaults to undefined|
| **forms** | [**string**] |  | (optional) defaults to undefined|
| **isKeyword** | [**boolean**] |  | (optional) defaults to true|
| **page** | [**number**] |  | (optional) defaults to 0|
| **size** | [**number**] |  | (optional) defaults to 20|
| **language** | [**string**] |  | (optional) defaults to 'VI'|


### Return type

**GrammarListResponse**

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

