import { CATEGORIES } from "@/features/popular/constants/categories.ts";
import {useNavigate} from "react-router-dom";

type LibraryHeaderPartProps = {
    type: "deck" | "folder";
    name?: string;
    ownerId?: string;
    ownerName?: string;
    createdAt?: string;
    updatedAt?: string;
    language?: string;
};

const formatDate = (iso?: string) =>
    iso ? new Date(iso).toLocaleString() : "—";

export default function ContainerHeaderPart({
                                              type,
                                              name,
                                              ownerId,
                                              ownerName,
                                              createdAt,
                                              updatedAt,
                                              language,
                                          }: LibraryHeaderPartProps) {
    const category = CATEGORIES[type];
    const Icon = category.icon;

    // TODO: add real route of owner page
    const navigate = useNavigate();
    const seeOwner = () => {
        navigate("" + ownerId);
    }

    return (
        <div className="space-y-4">
            <div className="flex flex-wrap items-center gap-2">
                <span className="flex items-center gap-1.5 rounded-full bg-[#1e1b3a] px-2.5 py-1 text-xs font-medium text-[#a5adf0]">
                    <Icon className="h-3.5 w-3.5" />
                    {type === "deck" ? "Deck" : "Folder"}
                </span>

                {language && (
                    <span className="rounded-full bg-muted px-2.5 py-1 text-xs font-medium text-muted-foreground">
                        {language}
                    </span>
                )}
            </div>

            <h1 className="break-words text-4xl font-bold">{name}</h1>

            <div className="flex flex-wrap gap-x-6 gap-y-1 text-sm text-muted-foreground">
                <span
                    className="cursor-pointer"
                    onClick={seeOwner}>
                    Owner: <span className="text-[#a5adf0]">{ownerName ?? "—"}</span>
                </span>
                <span>
                    Created: <span className="text-[#a5adf0]">{formatDate(createdAt)}</span>
                </span>
                <span>
                    Updated: <span className="text-[#a5adf0]">{formatDate(updatedAt)}</span>
                </span>
            </div>
        </div>
    );
}