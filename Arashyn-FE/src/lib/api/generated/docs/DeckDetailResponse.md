# DeckDetailResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **string** |  | [optional] [default to undefined]
**name** | **string** |  | [optional] [default to undefined]
**description** | **string** |  | [optional] [default to undefined]
**language** | **string** |  | [optional] [default to undefined]
**ownerId** | **string** |  | [optional] [default to undefined]
**isPublic** | **boolean** |  | [optional] [default to undefined]
**folders** | [**Set&lt;FolderSummariseResponse&gt;**](FolderSummariseResponse.md) |  | [optional] [default to undefined]
**grammars** | [**Set&lt;GrammarSummaryResponse&gt;**](GrammarSummaryResponse.md) |  | [optional] [default to undefined]
**createdAt** | **string** |  | [optional] [default to undefined]
**updatedAt** | **string** |  | [optional] [default to undefined]

## Example

```typescript
import { DeckDetailResponse } from 'arashyn-api';

const instance: DeckDetailResponse = {
    id,
    name,
    description,
    language,
    ownerId,
    isPublic,
    folders,
    grammars,
    createdAt,
    updatedAt,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
