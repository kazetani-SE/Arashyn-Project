# FolderDetailResponse


## Properties

Name | Type | Description | Notes
------------ | ------------- | ------------- | -------------
**id** | **string** |  | [optional] [default to undefined]
**name** | **string** |  | [optional] [default to undefined]
**ownerId** | **string** |  | [optional] [default to undefined]
**isPublic** | **boolean** |  | [optional] [default to undefined]
**decks** | [**Set&lt;DeckSummariseResponse&gt;**](DeckSummariseResponse.md) |  | [optional] [default to undefined]
**childFolders** | [**Set&lt;FolderSummariseResponse&gt;**](FolderSummariseResponse.md) |  | [optional] [default to undefined]
**parentFolders** | [**Set&lt;FolderSummariseResponse&gt;**](FolderSummariseResponse.md) |  | [optional] [default to undefined]
**createdAt** | **string** |  | [optional] [default to undefined]
**updatedAt** | **string** |  | [optional] [default to undefined]

## Example

```typescript
import { FolderDetailResponse } from 'arashyn-api';

const instance: FolderDetailResponse = {
    id,
    name,
    ownerId,
    isPublic,
    decks,
    childFolders,
    parentFolders,
    createdAt,
    updatedAt,
};
```

[[Back to Model list]](../README.md#documentation-for-models) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to README]](../README.md)
