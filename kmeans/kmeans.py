import argparse
import json
import numpy as np
import pandas as pd

from collections import defaultdict
from sklearn.cluster import KMeans
from sklearn.metrics import silhouette_score
from sklearn.preprocessing import OneHotEncoder, OrdinalEncoder, LabelEncoder

# set seed for reproducability.
np.random.seed(458567)

# Load data
parser = argparse.ArgumentParser(description='Extract clusters from input calls')
parser.add_argument(
    '--trace',
    required=True,
    type=str,
    help='the trace file path to extract calls from'
)
with open(parser.parse_args().trace) as json_file:
    trace = json.load(json_file)
inputs = [_t['inputs'] for _t in trace['executed_functions'][0]['calls']]
df = pd.DataFrame(inputs)

# Extract str and numbers columns indices
str_indices = df.dtypes[df.dtypes == 'object'].index.tolist()
numbers_indices = list(set(df.dtypes.index.tolist()).difference(set(str_indices)))

# Preprocess string features using one-hot encoding
ohe = OneHotEncoder()
str_features = ohe.fit_transform(df[str_indices])

# Concatenate the numerical features with the encoded categorical features and replace NaN with 0.
numerical_features = df[numbers_indices]
processed_data = np.nan_to_num(np.concatenate((str_features.toarray(), numerical_features), axis=1))

# Apply K-means clustering
sil_score = -2  # init to -2 since silhouette range is from -1 to +1
k = -1

# If only one value, do not try to compute a k-means, return one cluster.
if processed_data.shape[0] == 1:
    print('0')
else:
    for n_clusters in range(2, np.minimum(15, processed_data.shape[0])):
        # Cluster the data into n_clusters.
        kmeans = KMeans(n_clusters=n_clusters, n_init='auto')
        kmeans.fit(processed_data)
        cluster_labels = kmeans.labels_
        # Compute the silhouette score and store the k if it is better than the current one.
        score = silhouette_score(processed_data, cluster_labels, metric='euclidean')
        if score > sil_score:
            sil_score = score
            k = n_clusters

    # Let's clusterize with the best k amount of clusters.
    kmeans = KMeans(n_clusters=k, n_init='auto', init='k-means++')
    kmeans = kmeans.fit(processed_data)

    # Build a dict for each cluster
    clusters = defaultdict(list)
    for idx, _c in enumerate(kmeans.labels_):
        clusters[_c].append(idx)

    for key, value in clusters.items():
        print(value[0])
