export type Filter = {
    id: string;
    name: string;
};

export type ListSystemFilterResponse  = {
    systemFilters: Filter[];
};