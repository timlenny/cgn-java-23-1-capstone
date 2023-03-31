import React, {FC} from 'react';
import {Handle, Position} from 'reactflow';
import '../../style/home/reactFlow.css'

type DefaultNodeProps = {
    id: string;
    data: {
        label: string;
        size: number;
    };
}

const DefaultNode: FC<DefaultNodeProps> = ({data}) => {
    let nodeClassName;

    if (data.size === 1) {
        nodeClassName = "default-node1";
    } else if (data.size === 2) {
        nodeClassName = "default-node2";
    } else {
        nodeClassName = "default-node3";
    }
    return (
        <>
            <div className={nodeClassName}>
                {data.label}
            </div>
            <Handle type="target" position={Position.Top}/>
            <Handle type="source" position={Position.Bottom}/>
        </>
    );
};

export default React.memo(DefaultNode);